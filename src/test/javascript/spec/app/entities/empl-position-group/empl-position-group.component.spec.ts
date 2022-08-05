import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrTestModule } from '../../../test.module';
import { EmplPositionGroupComponent } from 'app/entities/empl-position-group/empl-position-group.component';
import { EmplPositionGroupService } from 'app/entities/empl-position-group/empl-position-group.service';
import { EmplPositionGroup } from 'app/shared/model/empl-position-group.model';

describe('Component Tests', () => {
  describe('EmplPositionGroup Management Component', () => {
    let comp: EmplPositionGroupComponent;
    let fixture: ComponentFixture<EmplPositionGroupComponent>;
    let service: EmplPositionGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplPositionGroupComponent],
      })
        .overrideTemplate(EmplPositionGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmplPositionGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmplPositionGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmplPositionGroup(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emplPositionGroups && comp.emplPositionGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
