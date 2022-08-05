import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { ContentTypeComponent } from 'app/entities/content-type/content-type.component';
import { ContentTypeService } from 'app/entities/content-type/content-type.service';
import { ContentType } from 'app/shared/model/content-type.model';

describe('Component Tests', () => {
  describe('ContentType Management Component', () => {
    let comp: ContentTypeComponent;
    let fixture: ComponentFixture<ContentTypeComponent>;
    let service: ContentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ContentTypeComponent],
      })
        .overrideTemplate(ContentTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContentTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContentType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contentTypes && comp.contentTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
