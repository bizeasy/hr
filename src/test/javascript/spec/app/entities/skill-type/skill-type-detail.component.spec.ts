import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { SkillTypeDetailComponent } from 'app/entities/skill-type/skill-type-detail.component';
import { SkillType } from 'app/shared/model/skill-type.model';

describe('Component Tests', () => {
  describe('SkillType Management Detail Component', () => {
    let comp: SkillTypeDetailComponent;
    let fixture: ComponentFixture<SkillTypeDetailComponent>;
    const route = ({ data: of({ skillType: new SkillType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [SkillTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SkillTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load skillType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skillType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
