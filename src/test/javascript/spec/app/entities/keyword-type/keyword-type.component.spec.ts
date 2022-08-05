import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { KeywordTypeComponent } from 'app/entities/keyword-type/keyword-type.component';
import { KeywordTypeService } from 'app/entities/keyword-type/keyword-type.service';
import { KeywordType } from 'app/shared/model/keyword-type.model';

describe('Component Tests', () => {
  describe('KeywordType Management Component', () => {
    let comp: KeywordTypeComponent;
    let fixture: ComponentFixture<KeywordTypeComponent>;
    let service: KeywordTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [KeywordTypeComponent],
      })
        .overrideTemplate(KeywordTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KeywordTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KeywordTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new KeywordType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.keywordTypes && comp.keywordTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
